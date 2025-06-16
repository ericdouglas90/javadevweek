package br.com.javadevweek.gestao_custos.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.javadevweek.gestao_custos.entity.Despesa;
import br.com.javadevweek.gestao_custos.repository.DespesaRepository;

@Service
public class CadastroDespesaUseCase {
    
    @Autowired
    private DespesaRepository despesaRepository;

    public Despesa execute(Despesa despesa) {
        
        if(despesa.getCategoria() == null || despesa.getData() == null || despesa.getEmail() == null || despesa.getCategoria() == null) {
            throw new IllegalArgumentException("Preencher todos os campos!");
        }

        despesa = despesaRepository.save(despesa);
        return despesa;
    }

}
