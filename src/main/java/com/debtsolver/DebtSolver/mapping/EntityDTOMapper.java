package com.debtsolver.DebtSolver.mapping;

import org.modelmapper.ModelMapper;

/**
 * Mapping class to generalize ModelMapper relationships between Entity <-> DTO
 *
 * @author Jason Wild
 */
public class EntityDTOMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * Maps a given Entity to a DTO
     *
     * @param entity: the Entity object to be mapped
     * @param dtoClass: the class file for the desired DTO

     * @return DTO object constructed from the given Entity
     */
    public static <T, D> D mapEntityToDTO(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Maps a given Entity to a DTO
     *
     * @param dto: the DTO object to be mapped
     * @param entityClass: the class file for the desired DTO

     * @return DTO object constructed from the given Entity
     */
    // Generic method for mapping any DTO to its entity
    public static <T, D> T mapDTOToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}

