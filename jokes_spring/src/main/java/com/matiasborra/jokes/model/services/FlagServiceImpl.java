package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.model.dao.IFlagDAO;
import com.matiasborra.jokes.model.entity.Flag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlagServiceImpl implements IFlagService {

    @Autowired
    private IFlagDAO flagDAO;

    @Override
    public List<Flag> findAllFlags() {
        return flagDAO.findAll();
    }

    @Override
    public Optional<Flag> findFlagById(Long id) {
        return flagDAO.findById(id);
    }

    @Override
    public Flag save(Flag flag) {
        return flagDAO.save(flag);
    }

    @Override
    public void deleteById(Long id) {
        flagDAO.deleteById(id);
    }
}
