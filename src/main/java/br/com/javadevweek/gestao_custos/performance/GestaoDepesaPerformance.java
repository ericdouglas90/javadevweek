package br.com.javadevweek.gestao_custos.performance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.javadevweek.gestao_custos.entity.Despesa;
import br.com.javadevweek.gestao_custos.repository.DespesaRepository;

@RequestMapping("/gestao/performance")
@RestController
@EnableCaching
public class GestaoDepesaPerformance {
    
    @Autowired
    DespesaRepository despesaRepository;

    @GetMapping("/sem-paginacao")
    public ResponseEntity<List<Despesa>> listarSemPaginacao() {
        long inicio = System.currentTimeMillis();
        var despesas = despesaRepository.findAll();

        long fim = System.currentTimeMillis();
        System.out.println("Tempo (sem paginação): " + (fim - inicio) + " ms");
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/com-paginacao")
    public ResponseEntity<Page<Despesa>> listarComPaginacao(Pageable pageable) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        var despesas = despesaRepository.findAll(pageable);

        stopWatch.stop();
        System.out.println("Tempo (com paginação): " + stopWatch.getTotalTimeMillis() + " ms");
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/com-paginacao/{email}")
    public ResponseEntity<Page<Despesa>> listarComPaginacaoEmail(@PathVariable String email, Pageable pageable) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        var despesas = despesaRepository.findByEmail(email, pageable);

        stopWatch.stop();
        System.out.println("Tempo (com paginação): " + stopWatch.getTotalTimeMillis() + " ms");
        return ResponseEntity.ok(despesas);
    }

    @Cacheable(value = "gastoPorEmailCache", key = "#email + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-'")
    @GetMapping("/cache/{email}")
    public ResponseEntity<Page<Despesa>> cacheComPaginacao(@PathVariable String email, Pageable pageable) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        var despesas = despesaRepository.findByEmail(email, pageable);

        stopWatch.stop();
        System.out.println("Tempo (com paginação): " + stopWatch.getTotalTimeMillis() + " ms");
        return ResponseEntity.ok(despesas);
    }
}
