package guru.springframework.msscbeerservice.services.inventory;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Profile("local-discovery")
// @FeignClient(name = "inventory-service", fallback = InventoryServiceFeignClientFailover.class, configuration = FeignClientConfig.class)
@FeignClient(name = "inventory-service", fallback = InventoryServiceFeignClientFailover.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = BeerInventoryServiceFeign.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(@PathVariable UUID beerId);
}