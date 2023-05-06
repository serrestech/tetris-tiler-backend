package com.hyperpunks.tetristilerbackend.web.services;

import com.hyperpunks.tetristilerbackend.libary.Shape;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class A3TaskService {
    class VariationResult {
        public final String name;
        public final String content;
        public VariationResult(String name, String content) {
            this.name = name;
            this.content = content;
        }
    }
    public ResponseEntity<Object> getVariations(String letter) {
        if (letter.length() != 1) {
            return ResponseEntity.badRequest().body("Letter parameter must be one character");
        }
        Shape shape = Shape.fromString(letter);
        if (shape == null) {
            return ResponseEntity.badRequest().body("Invalid letter parameter");
        }

//        Map<String, String> variations = new TreeMap<>();
        List<VariationResult> variations = new ArrayList<>();
        int i = 1;
        for (Shape variant : shape.generateAllVariants()) {
            variations.add(new VariationResult(letter + i, variant.toGridString()));
            i++;
        }
        return ResponseEntity.ok(variations);
    }
}
