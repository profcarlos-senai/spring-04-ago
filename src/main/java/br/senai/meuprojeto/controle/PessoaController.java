package br.senai.meuprojeto.controle;

import br.senai.meuprojeto.modelo.Pessoa;
import br.senai.meuprojeto.repositorio.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    // Retorna a lista completa de pessoas do banco
    @GetMapping
    public List<Pessoa> getPessoa() {
        return pessoaRepository.findAll();
    }

    // Retorna uma pessoa específica através do ID
    @GetMapping("{id}")
    public ResponseEntity<Pessoa> getPessoaPorId(@PathVariable Long id) {
        // funções do repository que podem retornar nulo usam Optional<Classe>
        Optional<Pessoa> opt = pessoaRepository.findById(id);
        if (opt.isPresent()) { // se achou a pessoa, opt.isPresent() é True
            Pessoa pessoa = (Pessoa)opt.get(); // pega a pessoa do optional
            return ResponseEntity.ok(pessoa); // retorna 200 (ok) + json da pessoa
        }
        return ResponseEntity.notFound().build(); // retorna 404 (não encontrado)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // se der certo, responde com 204 (criado)
    public Pessoa criaPessoa(@Valid @RequestBody Pessoa pessoa) {

        // se chegou vivo aqui, pode salvar
        pessoa = pessoaRepository.save(pessoa);

        // devolve a pessoa com status 200 (ok)
        return pessoa;
    }

    @PutMapping("{id}")
    public Pessoa alteraPessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        if(!id.equals(pessoa.getId())){ // o cabra mandou uma id na url e outra no json
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não pode alterar o id");
        }
        if (!pessoaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado.");
        }
        pessoa.setId(id);
        return pessoaRepository.save(pessoa);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaPessoa(@PathVariable Long id) {
        if (!pessoaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pessoaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

