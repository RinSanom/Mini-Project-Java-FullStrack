package model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.dto.ProductResponDto;
import model.repository.ProductRepository;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartItem {
    private ProductResponDto productResponDto;
    private int quantity;
}
