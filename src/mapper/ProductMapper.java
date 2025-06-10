package mapper;

import model.entities.ProductModel;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;

import java.util.Random;
import java.util.UUID;

public class ProductMapper {
    public static ProductResponDto mapFromProductToProductResponDto(ProductModel product) {
        return new ProductResponDto(product.getPName(), product.getPrice(), product.getQty(), product.getPUuid());
    }

    public static ProductModel mapFromProductCreateDtoToProduct(ProductCreateDto productCreateDto) {
        return new ProductModel(new Random().nextInt(999999999), productCreateDto.pName(), productCreateDto.price(), productCreateDto.qty(), false ,UUID.randomUUID().toString());
    }
}
