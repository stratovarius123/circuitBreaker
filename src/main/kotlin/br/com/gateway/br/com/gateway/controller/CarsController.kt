package br.com.gateway.br.com.gateway.controller

import br.com.gateway.br.com.gateway.model.Car
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class CarsController() {

    @GetMapping(value = ["/findCarDetails/{carId}"])
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    fun getCars(@PathVariable carId: Int): String {
        val response = RestTemplate().exchange(
                "http://apiCars/findCarDetails/{carId}",
                HttpMethod.GET,
                null,
                 object : ParameterizedTypeReference<Car>() {
                },carId)

        return response.body.toString()
    }

    fun fallbackMethod(carId: Int): String {
        return "Fallback response:: No car $carId details available temporarily"
    }

}