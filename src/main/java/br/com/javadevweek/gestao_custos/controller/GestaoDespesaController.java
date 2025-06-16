package br.com.javadevweek.gestao_custos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.javadevweek.gestao_custos.custom_messages.ErrorMessage;
import br.com.javadevweek.gestao_custos.entity.Despesa;
import br.com.javadevweek.gestao_custos.useCases.BuscarDespesaUseCase;
import br.com.javadevweek.gestao_custos.useCases.CadastroDespesaUseCase;

@RequestMapping("/gestao")
@RestController
public class GestaoDespesaController {

    /*
     * - Cadastro de despesa
     * - Criar tabela de despesa no banco de dados
     * - Criar entidade
     */

    @GetMapping("/teste")
    public void teste() {
        System.out.println("PING");
    }

    @Autowired
    CadastroDespesaUseCase cadastroDespesaUseCase;

    @Autowired
    BuscarDespesaUseCase buscarDespesaUseCase;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Despesa despesa) {

        try {
            var result = cadastroDespesaUseCase.execute(despesa);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage(), "INVALID_PARAMS"));
        }
    }

    @GetMapping("/{email}")
    public List<Despesa> findByEmaiAndDate(@PathVariable String email, @RequestParam(required = false) LocalDate data) {
        return buscarDespesaUseCase.execute(email, data);
    }

}
