package servicios;

import api.ProductoServicio;
import modelo.Categoria;
import modelo.Marca;
import modelo.Producto;

import javax.persistence.*;
import java.util.List;

public class ProductoService implements ProductoServicio {

    private EntityManagerFactory emf; //= Persistence.createEntityManagerFactory("objectdb:myDbFile.odb");
    private EntityManager em;//= emf.createEntityManager();
    private EntityTransaction tx;//= em.getTransaction();

    public ProductoService(String servicio) {
        this.emf = Persistence.createEntityManagerFactory(servicio);

    }

    @Override
    public void crearProducto(String codigo, String descripcion, float precio, Long IdCategoria, Long IdMarca) {
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();
        List<Producto> productos;
        Marca marca;
        Categoria categoria;
        try {
            tx.begin();
            TypedQuery<Marca> q = em.createQuery("select m from Marca m where m.id =: id", Marca.class);
            q.setParameter("id", IdMarca);
            marca = q.getSingleResult();
            if (marca == null)
                throw new RuntimeException("La marca no esta registrado.");

            TypedQuery<Categoria> qc = em.createQuery("select ca from Categoria ca where ca.id=: id", Categoria.class);
            qc.setParameter("id", IdCategoria);
            categoria = qc.getSingleResult();
            if (categoria == null)
                throw new RuntimeException("La categoria no esta registrado.");

            TypedQuery<Producto> qp = em.createQuery("select p from Producto p where p.codigo=:codigo", Producto.class);
            qp.setParameter("codigo", codigo);
            productos = qp.getResultList();
            if (!productos.isEmpty())
                throw new RuntimeException("El codigo de producto ingresado ya esta registrado.");
            Producto producto = new Producto(codigo, precio, descripcion, categoria, marca);
            em.persist(producto);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (em.isOpen())
                em.close();
//            if (emf.isOpen())
//                emf.close();
        }
    }

    @Override
    public void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long IdCategoria,
            Long IdMarca) {
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();
        List<Producto> productos;

        try {
            tx.begin();

            TypedQuery<Producto> q = em.createQuery("select p from Producto p where p.id=:id", Producto.class);
            q.setParameter("id", idProducto);
            productos = q.getResultList();
            if (productos.isEmpty())
                throw new RuntimeException("El producto no esta registrado.");

            Producto producto = em.find(Producto.class, idProducto);

            TypedQuery<Producto> qp = em.createQuery("select p from Producto p where p.codigo=:codigo", Producto.class);
            qp.setParameter("codigo", codigo);
            if (!qp.getResultList().isEmpty() && !producto.codigo().equals(codigo)) {
                throw new RuntimeException("Ya Existe este codigo de producto");
            }

            Marca marca = em.find(Marca.class, IdMarca);
            Categoria categoria = em.find(Categoria.class, IdCategoria);

            producto.setCategoria(categoria);
            producto.setCodigo(codigo);
            producto.setDescripcion(descripcion);
            producto.setMarca(marca);
            producto.setPrecio(precio);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (em.isOpen())
                em.close();
//            if (emf.isOpen())
//                emf.close();
        }
    }

    @Override
    public List listarProductos() {
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();
        List<Producto> productos;

        try {
            tx.begin();

            TypedQuery<Producto> qp = em.createQuery("select p from Producto p", Producto.class);
            productos = qp.getResultList();
            if (productos.isEmpty())
                throw new RuntimeException("No hay productos registrados.");

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (em.isOpen())
                em.close();
//            if (emf.isOpen())
//                emf.close();
        }
        return productos;
    }

    @Override
    public void crearCategoria(String nombre) {
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();

        try {
            tx.begin();

           Categoria categoria = new Categoria(nombre);
            em.persist(categoria);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (em.isOpen())
                em.close();
//            if (emf.isOpen())
//                emf.close();
        }
    }

    @Override
    public void crearMarca(String nombre) {
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();

        try {
            tx.begin();

            Marca marca = new Marca(nombre);
            em.persist(marca);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            if (em.isOpen())
                em.close();
//            if (emf.isOpen())
//                emf.close();
        }
    }

}
