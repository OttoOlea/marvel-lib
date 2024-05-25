package com.marvel.service;

import com.marvel.dto.MarvelResponse;
import com.marvel.exception.MarvelException;
import org.springframework.stereotype.Service;

@Service
public interface MarvelService {

    public MarvelResponse getAllCharaters() throws MarvelException;

    public MarvelResponse getCharaterById(String charaterId) throws MarvelException;


}
