package guru.springframework.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = true)
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {
	
	public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    @SuppressWarnings("unused")
	private final RestTemplate restTemplate;
    
    private String beerInventoryServiceHost;

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
    	
    	this.restTemplate = restTemplateBuilder.build();
    }

	@Override
	public Integer getOnhandInventory(UUID beerId) {
		
		ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate
				.exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<BeerInventoryDto>>(){}, (Object) beerId);
		
		Integer onhand = Objects.requireNonNull(responseEntity.getBody())
				.stream()
				.mapToInt(BeerInventoryDto::getQuantityOnHand)
				.sum();
				
		return onhand;
	}
}