package com.ledzion.customerservice.repository;

import com.ledzion.customerservice.model.Bicycle;
import com.ledzion.customerservice.model.Size;
import com.ledzion.customerservice.model.Type;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BicycleDefaultDAO implements BicycleDAO {

    @Override
    public Optional<Bicycle> getBicycleById(long id) {
        return Optional.of(new Bicycle(1, Type.CITY, Size.S ));
    }

    @Override
    public List<Bicycle> getAllBicycles() {
        return new ArrayList<>(Arrays.asList(
                new Bicycle(1, Type.CITY, Size.S ),
                new Bicycle(1, Type.CROSS, Size.M ),
                new Bicycle(1, Type.CITY, Size.M )
        ));
    }

    @Override
    public List<Bicycle> getBicyclesByType(String type, String size) {
        return getAllBicycles().stream()
                .filter(b -> Type.getType(type).equals(b.getType()))
                .filter(b -> Objects.nonNull(size) && Type.getType(type).equals(b.getType()))
                .collect(Collectors.toList());
    }

    public List<Bicycle> getBicyclesByType2(List<String> types, List<String> sizes) {
        return getAllBicycles().stream()
                .filter(b -> types.contains(b.getType().getTypeDescription()))
                .filter(b -> sizes.contains(b.getSize().getSizeDescription()))
                .collect(Collectors.toList());
    }
}
