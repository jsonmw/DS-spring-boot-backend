package com.debtsolver.DebtSolver.util;

import org.modelmapper.ModelMapper;

public class MappingUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * Maps a given object to a new class
     *
     * @param object:   the object to be mapped
     * @param newClass: the class file for the desired mapping
     * @return object constructed from the given class
     */
    public static <T, D> D mapToNewClass(T object, Class<D> newClass) {
        return modelMapper.map(object, newClass);
    }
}
