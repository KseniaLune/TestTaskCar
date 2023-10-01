# Car guide

REST API.
Data base - PostgresSQL

### Launch
```bash
# docker compose up
```

### Layers
Controller - HTTP endpoints/mapping</br>
Service - Domain logic</br>
Dao - Storage abstraction</br>
Repo - Specific storage implementation</br>

### API methods

***GET /api/v1/car:*** </br>
Return sorted list of cars from data base. The method accepts the following parameters: </br>
1. brand
2. color
3. year_of_manufacture
4. country
5. mileage_from
6. mileage_to
7. trunk_volume_from
8. trunk_volume_to
9. sort_by (only number_plate or year_of_manufacture or mileage)

***POST /api/v1/car/add:*** </br>
Add car to data base.</br>
Input:   

```
{
    "number_plate":"A160AA111",
    "brand":"Nelada",
    "color":"White",
    "year_of_manufacture":"1999",
    "country":"Russia",
    "trunk_volume":"100",
    "mileage":"1234567"
}
```

***PUT /api/v1/car/{number_plate}:*** </br>
Update car. Use te number_plate of car in path.</br>
Input:

```   
{
    "color":"White",
    "mileage":"1234567"
}
```


***DELETE /api/v1/car/{number_plate}:*** </br>
Delete car. Use te number_plate of car in path.</br>
</br>

***GET /api/v1/car/statistic:*** </br>
Return the car statistic:</br>
Output: 

```
{
    "number_of_cars": 5,
    "number_by_brand": {
        "Citroen": 2,
        "Renault": 1,
        "Peugeot": 2
        },
    "number_by_country": {
        "China": 3,
        "France": 2
        },
    "number_by_year": {
        "2023": 5
        }
    }

```
