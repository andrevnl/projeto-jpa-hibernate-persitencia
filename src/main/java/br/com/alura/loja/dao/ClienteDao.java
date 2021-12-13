package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDao {

    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void salvar(Cliente cliente) {
        this.em.persist(cliente);
    }

    public void atualizar(Cliente cliente) {
        this.em.merge(cliente);
    }

    public void remover(Cliente cliente) {
        this.em.remove(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        String jpql = "SELECT p FROM Cliente p";
        return em.createQuery(jpql, Cliente.class).getResultList();
    }
}
