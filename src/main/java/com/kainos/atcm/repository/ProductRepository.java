package com.kainos.atcm.repository;

import com.kainos.atcm.domain.product.Product;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepository {
    private HashMap<UUID, Product> dataStore = new HashMap<>();

    public ProductRepository() {
        this.populateExampleProducts();
    }

    public Collection<Product> getProducts() {
        return dataStore.values();
    }

    public Product getProduct(UUID productId) {
        return dataStore.get(productId);
    }


    public List<Product> getProductByName(String productName) {
        Collection<Product> products = dataStore.values();
        List<Product> filtered = products.stream().filter(p -> p.getName().contains(productName)).collect(Collectors.toList());
        return filtered;
    }

    private void addProductToStore(String productId, String name, String description, int cost, String category) {
        Product productToAdd = new Product();
        productToAdd.setId(UUID.fromString(productId));
        productToAdd.setName(name);
        productToAdd.setDescription(description);
        productToAdd.setCost((float) cost);
        productToAdd.setCategory(category);
        dataStore.put(productToAdd.getId(), productToAdd);
    }

    private void populateExampleProducts() {
        addProductToStore("285e29f2-29e2-4651-bc08-4d7e0e922368", "Armor of the Kind Regent", "Smite will now also be cast at a second nearby enemy.", 50, "Armour");
        addProductToStore("0aa48472-b772-481d-a1b1-8969515bcc2f", "Chaingmail", "After earning a survivial bonus, quickly heal to full Life.", 90, "Armour");
        addProductToStore("9f323a1d-fce2-450d-8a96-3753f79f3cbc", "Cindercoat", "Reduce the resource cost of Fire Skills", 40, "Armour");
        addProductToStore("1efba252-e84d-45fc-9373-0136592b4461", "Goldskin", "Chance for enemies to drop gold when you hit them.", 10, "Armour");
        addProductToStore("dc01f67d-64db-4dd3-8064-1042923cace6", "Shi Mizu's Haori ", "All attacks are guaranteed Critical Hits.", 100, "Armour");
        addProductToStore("20c329c9-43e1-43f0-9b77-e66cb695232d", "Fire Walkers", "Burn the ground you walk on.", 20, "Boots");
        addProductToStore("917d6f12-035a-4f58-b009-a772a6a7b919", "Ice Climbers", "Gain immunity to Freeze and Immobilize effects.", 40, "Boots");
        addProductToStore("203ff38b-46bc-4699-af00-1fb048873df8", "The Undisputed Champion", "Frenzy gains the effect of every rune.", 60, "Belts");
        addProductToStore("581d2ad1-c86b-481d-9c5a-8592d269df64", "Dread Iron", "Ground Stomp causes an Avalanche.", 30, "Belts");
        addProductToStore("b8f4bc6a-602a-4623-a653-4663c687686f", "Hammer Jammers", "Increases Movement Speed", 85, "Pants");
    }
}
