const express = require("express");
const CoffeeManager = require("./coffee-manager");
const app = express();
const Status = require("./status");

const PORT = 3000;

app.route("/brew")
    .get((req, res, next) => {
        console.log("GET /brew");
    })
    .post((req, res, next) => {
        console.log("New brew request");
        res.json({
            status: Status.OK,
            order: CoffeeManager.addOrder()
        });
    })

app.route("/brew/:orderNum")
    .get((req, res, next) => {
        console.log("GET /brew/" + req. params.orderNum);
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

app.listen(PORT, () => {
    console.log("Listening on port " + PORT);
})