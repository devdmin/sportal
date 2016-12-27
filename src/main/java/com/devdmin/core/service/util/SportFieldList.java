package com.devdmin.core.service.util;

import com.devdmin.core.model.SportField;

import java.util.ArrayList;
import java.util.List;

public class SportFieldList {
    private List<SportField> sportFieldList = new ArrayList<SportField>();

    public SportFieldList(List<SportField> sportFieldList) {
        this.sportFieldList = sportFieldList;
    }

    public List<SportField> getSportFieldList() {
        return sportFieldList;
    }

    public void setSportFieldList(List<SportField> sportFieldList) {
        this.sportFieldList = sportFieldList;
    }
}
