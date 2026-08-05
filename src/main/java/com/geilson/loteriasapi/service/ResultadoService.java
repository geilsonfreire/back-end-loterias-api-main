package com.geilson.loteriasapi.service;

import com.geilson.loteriasapi.model.Resultado;
import com.geilson.loteriasapi.model.ResultadoId;
import com.geilson.loteriasapi.repository.ResultadoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ResultadoService {

    private final ResultadoRepository repository;

    public ResultadoService(ResultadoRepository repository) {
        this.repository = repository;
    }

    @Cacheable("resultados")
    public List<Resultado> findByLoteria(String loteria) {
        return repository.findById_Loteria(loteria)
                .stream()
                .sorted(Comparator.comparing(Resultado::getConcurso).reversed())
                .toList();
    }

    public Resultado findByLoteriaAndConcurso(String loteria, int concurso) {
        return repository.findById(new ResultadoId(loteria, concurso))
                .orElse(null);
    }

    public Resultado findLatest(String loteria) {
        return repository.findTopById_Loteria(loteria)
                .orElse(null);
    }

    public void save(Resultado resultado) {
        repository.save(resultado);
    }

    public void saveAll(List<Resultado> resultados) {
        repository.saveAll(resultados);
    }
}