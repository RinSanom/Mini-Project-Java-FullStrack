package model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.dto.ProductResponDto;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartItem {
    private ProductResponDto productResponDto;
    private Integer quantity;
}
