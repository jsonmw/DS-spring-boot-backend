package com.debtsolver.DebtSolver.mapping;

import org.modelmapper.ModelMapper;

/**
 * Mapping class to generalize ModelMapper relationships between Request <-> DTO <-> Response
 *
 * @author Jason Wild
 */
public class RequestResponseDTOMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * Maps a given Request to a DTO
     *
     * @param request:  the request object to be mapped
     * @param dtoClass: the class file for the desired DTO
     * @return DTO object constructed from the given Entity
     */
    public static <T, D> D mapRequestToDTO(T request, Class<D> dtoClass) {
        return modelMapper.map(request, dtoClass);
    }

    /**
     * Maps a given DTO to a Response
     *
     * @param dto:           the DTO object to be mapped
     * @param responseClass: the class file for the desired response
     * @return DTO object constructed from the given Entity
     */
    // Generic method for mapping any DTO to its request
    public static <T, D> T mapDTOToResponse(D dto, Class<T> responseClass) {
        return modelMapper.map(dto, responseClass);
    }
}