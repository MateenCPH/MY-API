package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.PlantDTO;
import dat.entities.Hotel;
import dat.entities.Plant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlantDAO implements IDAO<PlantDTO, Integer> {

    private static PlantDAO instance;
    private static EntityManagerFactory emf;

    public static PlantDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PlantDAO();
        }
        return instance;
    }

    @Override
    public PlantDTO readById(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Plant plant = em.find(Plant.class, integer);
            return new PlantDTO(plant);
        }
    }

    @Override
    public List<PlantDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<PlantDTO> query = em.createQuery("SELECT new dat.dtos.PlantDTO(p) FROM Plant p", PlantDTO.class);
            return query.getResultList();
        }
    }

    public List<PlantDTO> readByType(String s) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<PlantDTO> query = em.createQuery("SELECT new dat.dtos.PlantDTO(p) FROM Plant p WHERE p.plantType = :type", PlantDTO.class);
            query.setParameter("type", s);
            return query.getResultList();
        }
    }

    @Override
    public PlantDTO create(PlantDTO plantDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Plant plant = new Plant(plantDTO);
            em.persist(plant);
            em.getTransaction().commit();
            return new PlantDTO(plant);
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public PlantDTO update(Integer i, PlantDTO plantDTO) {
        return null;
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Plant plant = em.find(Plant.class, integer);
            return plant != null;
        }
    }
}
