package model.service;

import mapper.ProductMapper;
import model.antities.ProductModel;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.repository.ProductRepository;
import model.repository.ProductRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    @Override
    public List<ProductResponDto> getAllProducts() {
        List<ProductResponDto> productResponDtoList = new ArrayList<>();
        productRepository.getAll()
                .stream()
                .forEach(p->{
                    productResponDtoList
                            .add(new ProductResponDto(p.getPName()
                            ,p.getPrice()
                                    ,p.getQty()
                                    ,p.isDeleted()
                                    ,p.getPUuid()
                                    ));
                });
        return productResponDtoList;
    }

    @Override
    public ProductResponDto insertNewProduct(ProductCreateDto productModel) {
        ProductModel productModel1
                = ProductMapper.mapFromProductCreateDtoToProduct(productModel);
        return ProductMapper.mapFromProductToProductResponDto(
                productRepository.save(productModel1)
        );
    }

    @Override
    public ProductResponDto getProductByName(String productName) {
     return ProductMapper.mapFromProductToProductResponDto(productRepository.fineProductByName(productName));
    }

    @Override
    public ProductResponDto getProductByCategory(String productCategory) {
        return null;
    }

    @Override
    public List<ProductRepository> getAllProductsInCart() {
        return List.of();
    }

    @Override
    public ProductRepository addProductToCart(String productUuid,Integer quantity) {
       ProductModel productRepo = productRepository.fineProductByUuid(productUuid);

        if(productRepo.getQty() >= quantity){
            productRepo.setQty(productRepo.getQty()-quantity);
            ProductResponDto product = ProductMapper.mapFromProductToProductResponDto(productRepo);
        }else {
            System.out.println("[!] Your Quantity is over stock!");
        }
        return null;
    }


}
