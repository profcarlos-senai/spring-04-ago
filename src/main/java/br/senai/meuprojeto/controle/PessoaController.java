package br.senai.meuprojeto.controle;

import br.senai.meuprojeto.modelo.Pessoa;
import br.senai.meuprojeto.repositorio.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> criaPessoa(@RequestBody Pessoa pessoa) {
        // cria um mapa de campo/erro caso tenha erros pra devolver
        Map<String, String> erros = new HashMap<>();
        // testa os dados
        if (pessoa.getNome() == null || pessoa.getNome().isBlank()) {
            erros.put("nome","Nome não pode ficar em branco");
        }
        if (pessoa.getIdade() != null && pessoa.getIdade() <= 0) {
            erros.put("idade", "Nem nasceu ainda e já tá cadastrado num sistema. Tadinho :'(");
        }
        if (pessoa.getDataContrato().isAfter(LocalDate.now())){
            erros.put("dataContrato", "Data de contratação não pode ser no futuro");
        }
        // se tiver erros, retorna 400 bad request
        if(!erros.isEmpty()){
            return ResponseEntity.badRequest().body(erros);
        }

        // se chegou vivo aqui, pode salvar
        pessoa = pessoaRepository.save(pessoa);

        // devolve a pessoa com status 200 (ok)
        return ResponseEntity.ok(pessoa);
    }

    @PutMapping("{id}")
    public ResponseEntity<Pessoa> alteraPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        if(pessoa.getId() != null && !id.equals(pessoa.getId())){ // o cabra mandou uma id na url e outra no json
            return ResponseEntity.badRequest().build();
        }
        if (!pessoaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pessoa.setId(id);
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
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

