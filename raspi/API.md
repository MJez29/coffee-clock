# Keurig Machine Server API

The server will be running on port 3000

## Response Format
Responses will always be JSON and will have a [status](#statuses) field attached.
```typescript
response = {
    status: number,
    ...
}
```

## Statuses
Possible values that the server will return for the status in the return body:
- `OK: 0`
- `INVALID_ORDER_NUMBER: 1`
- `CANNOT_DELETE_ORDER: 2`

## Order Format
An `Order` object will always appear as follows
```javascript
{
    orderNum: number,
    delay: number,          // In milliseconds
    status: number          // IN_QUEUE = 0; BREWING = 1; DONE = 2;
}
```

## `/brew`

**GET** 
Returns the current number of orders in queue and the expected time until completion of all brew jobs*

**POST** 
Adds a new brew job to the Keurig. It expects a request body of the form:
```javascript
req.body = {
    coffeeSize: string          // "Small", "Medium" or "Large"
}
```
Returns JSON of the following form:
```javascript
{
    orderNum: number,       // The order number to use in follow up requests
    delay: number           // The estimated delay until the coffee begins brewing
}
```

## `/brew/:orderNum`

**GET**  
Returns the status of the order (whether its in queue, being brewed or has finished)*

**DELETE**  
Cancels an order if it is still in queue. 
Possible return statuses are:
- `Status.OK` when the order is deleted successfully
- `Status.CANNOT_DELETE_ORDER` when the order is being currently being brewed
- `Status.INVALID_ORDER_NUMBER` if the order cannot be found which could be because
    - The order was previously deleted
    - orderNum cannot be converted to an integer
    - The order has already been brewed

# Status