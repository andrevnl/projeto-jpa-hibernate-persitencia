package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void salvar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public void atualizar(Pedido pedido) {
        this.em.merge(pedido);
    }

    public void remover(Pedido pedido) {
        this.em.remove(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> buscarTodos() {
        String jpql = "SELECT p FROM Pedido p";
        return em.createQuery(jpql, Pedido.class).getResultList();
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas() {
        String jpql = "select new br.com.alura.loja.vo.RelatorioDeVendasVo(" +
                "produto.nome, SUM(item.quantidade), MAX(pedido.data)) " +
                "from Pedido pedido " +
                "join pedido.itens item " +
                "join item.produto produto " +
                "group by produto.nome " +
                "order by item.quantidade desc";

        return em.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id) {
        return em.createQuery("select p from Pedido p join fetch p.cliente where p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
