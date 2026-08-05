package com.geilson.loteriasapi.rest;

import com.geilson.loteriasapi.model.Loteria;
import com.geilson.loteriasapi.model.Resultado;
import com.geilson.loteriasapi.model.exception.ResourceNotFoundException;
import com.geilson.loteriasapi.service.ResultadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@Tag(name = "Loterias")
public class ApiRestController {

    private static final String INVALID_LOTTERY_MESSAGE =
            "'%s' não é o id de nenhuma das loterias suportadas. Loterias suportadas: %s";

    private static final String RESULT_NOT_FOUND_MESSAGE =
            "Resultado não encontrado para \"%s\", concurso \"%d\"";

    private static final String NO_RESULTS_FOUND_MESSAGE =
            "Nenhum resultado encontrado para a loteria %s";

    private final List<String> lotteries = Loteria.asList();

    private final String ALLOWABLE_VALUES =
            "maismilionaria, megasena, lotofacil, quina,"
            + " lotomania, timemania, duplasena, federal, diadesorte, supersete";

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping
    @Operation(
            summary = "Retorna todas as loterias disponíveis para pesquisa."
    )
    public ResponseEntity<List<String>> getLotteries() {
        return ResponseEntity.ok(Loteria.asList());
    }

    @GetMapping("{loteria}")
    @Operation(
            summary = "Retorna todos os resultados já realizados da loteria especificada."
    )
    public ResponseEntity<List<Resultado>> getResultsByLottery(
            @PathVariable
            @Parameter(
                    description = "Identificador da loteria",
                    required = true,
                    example = "lotofacil"
            )
            String loteria) {

        validateLottery(loteria);

        return ResponseEntity.ok(resultadoService.findByLoteria(loteria));
    }

    @GetMapping("{loteria}/{concurso}")
    @Operation(
            summary = "Retorna o resultado da loteria e concurso especificado."
    )
    public ResponseEntity<Resultado> getResultById(
            @PathVariable
            @Parameter(
                    description = "Identificador da loteria",
                    required = true,
                    example = "lotofacil"
            )
            String loteria,

            @PathVariable
            @Parameter(
                    description = "Número do concurso",
                    required = true,
                    example = "3500"
            )
            Integer concurso) {

        validateLottery(loteria);

        Resultado resultado =
                resultadoService.findByLoteriaAndConcurso(loteria, concurso);

        if (resultado == null) {
            throw new ResourceNotFoundException(
                    RESULT_NOT_FOUND_MESSAGE.formatted(loteria, concurso));
        }

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("{loteria}/latest")
    @Operation(
            summary = "Retorna o resultado mais recente da loteria especificada."
    )
    public ResponseEntity<Resultado> getLatestResult(
            @PathVariable
            @Parameter(
                    description = "Identificador da loteria",
                    required = true,
                    example = "lotofacil"
            )
            String loteria) {

        validateLottery(loteria);

        Resultado resultado = resultadoService.findLatest(loteria);

        if (resultado == null) {
            throw new ResourceNotFoundException(
                    NO_RESULTS_FOUND_MESSAGE.formatted(loteria));
        }

        return ResponseEntity.ok(resultado);
    }

    private void validateLottery(String loteria) {
        if (!lotteries.contains(loteria)) {
            throw new ResourceNotFoundException(
                    INVALID_LOTTERY_MESSAGE.formatted(loteria, lotteries));
        }
    }
}