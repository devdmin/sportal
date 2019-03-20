package com.devdmin.core.service.util;

import com.devdmin.core.model.SportField;
import com.devdmin.rest.controller.dto.SportFieldDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SportFieldDtoList {
    private Set<SportFieldDto> sportFieldList = new HashSet<SportFieldDto>();

    public SportFieldDtoList(Set<SportFieldDto> sportFieldList) {
        this.sportFieldList = sportFieldList;
    }

    public Set<SportFieldDto> getSportFieldList() {
        return sportFieldList;
    }

    public void setSportFieldList(Set<SportFieldDto> sportFieldList) {
        this.sportFieldList = sportFieldList;
    }
}
