const express = require("express");
const CoffeeManager = require("./coffee-manager");
const app = express();
const Status = require("./status");
const bodyParser = require("body-parser");

const PORT = 3000;

app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

/**
 * Handlers for the route /brew
 */
app.route("/brew")
    /**
     * Returns general information about the Keurig, such as expected wait until a new order can be brewed
     */
    .get((req, res, next) => {
        let status = CoffeeManager.getStatus();
        res.json({
            status: Status.OK,
            numOrders: status.numOrders,
            delay: status.delay
        });
    })
    /**
     * A POST request is sent to signal a new order
     * It sends back order information
     */
    .post((req, res, next) => {
        console.log("POST /brew" + JSON.stringify(req.body));
        try {
            res.json({
                status: Status.OK,
                order: CoffeeManager.addOrder(req.body.coffeeSize)
            });
        } catch (e) {
            res.json({
                status: e
            })
        }
    })

/**
 * Routes to handle a specific order
 */
app.route("/brew/:orderNum")
    /**
     * Returns general information about the specified order
     */
    .get((req, res, next) => {
        console.log("GET /brew/" + req.params.orderNum);
        try {
            // Tries to find the order attached to the given order number
            res.json({
                status: Status.OK,
                order: CoffeeManager.getOrder(req.params.orderNum)
            });
        } catch (e) {
            // If the order doesn't exist then an error is thrown
            if (e == Status.INVALID_ORDER_NUMBER) {
                // Error is sent back
                res.json({
                    status: Status.INVALID_ORDER_NUMBER
                });
            }
        }
    })
    /**
     * Attempts to delete a coffee order. It sends back the outcome of the delete attempt
     */
    .delete((req, res, next) => {
        console.log("DELETE /brew/" + req.params.orderNum);
        res.json({
            status: CoffeeManager.deleteOrder(req.params.orderNum)
        });
    })

// Starts the server
app.listen(PORT, () => {
    console.log("Listening on port " + PORT);
})