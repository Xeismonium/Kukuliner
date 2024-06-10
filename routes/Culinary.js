const express = require("express");
const router = express.Router();
const Culinary = require("../services/Culinary");

router.get("/", async function (req, res, next) {
  try {
    res.json(await Culinary.getMultiple(req.query));
  } catch (err) {
    console.error(`Error while getting culinary `, err.message);
    next(err);
  }
});

router.get("/:id", async function (req, res, next) {
  try {
    const id = req.params.id;
    const data = await Culinary.getSingle(id);
    res.json(data);
  } catch (err) {
    console.error(`Error while getting culinary `, err.message);
    next(err);
  }
});

router.post("/", async function (req, res, next) {
  try {
    res.json(await Culinary.create(req.body));
  } catch (err) {
    console.error(`Error while creating culinary`, err.message);
    next(err);
  }
});

router.put("/:id", async function (req, res, next) {
  try {
    res.json(await Culinary.update(req.params.id, req.body));
  } catch (err) {
    console.error(`Error while updating culinary`, err.message);
    next(err);
  }
});

router.delete("/:id", async function (req, res, next) {
  try {
    res.json(await Culinary.remove(req.params.id));
  } catch (err) {
    console.error(`Error while deleting culinary`, err.message);
    next(err);
  }
});

module.exports = router;
