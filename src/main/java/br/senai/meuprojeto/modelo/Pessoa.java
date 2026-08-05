package br.senai.meuprojeto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ficar em branco")
    private String nome;

    @NotBlank(message = "Telefone não pode ficar em branco")
    private String telefone;

    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Nem nasceu ainda e já tá cadastrado num sistema. Tadinho :'(")
    private Integer idade;

    @NotNull(message = "Data de contrato é obrigatória")
    @PastOrPresent(message = "Data de contratação não pode ser no futuro")
    private LocalDate dataContrato;

    @Positive(message = "Salário deve ser maior que zero")
    private BigDecimal salario;

    // Construtor vazio (obrigatório para o JPA)
    public Pessoa() {
    }

    // Construtor completo
    public Pessoa(Long id, String nome, String telefone, Integer idade, LocalDate dataContrato, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.idade = idade;
        this.dataContrato = dataContrato;
        this.salario = salario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public LocalDate getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(LocalDate dataContrato) {
        this.dataContrato = dataContrato;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}