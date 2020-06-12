import React, {useContext} from "react";
import {BasketContext} from "../../context/basketContext/BasketContext";
import Button from "@material-ui/core/Button";

export default function BasketDebug() {
    const {
        getBasketMovies,
        getBasketPayment,
        addMovieToBasket,
        removeMovieFromBasket,
        setPayment
    } = useContext(BasketContext);


    return (
        <>
            <h2>Basket debug</h2>
            <Button variant="outlined" onClick={() => addMovieToBasket("1")} color="secondary">Add movie 1</Button>
            <Button variant="outlined" onClick={() => addMovieToBasket("2")} color="secondary">Add movie 2</Button>
            <Button variant="outlined" onClick={() => addMovieToBasket("3")} color="secondary">Add movie 3</Button>
            <Button variant="outlined" onClick={() => addMovieToBasket("4")} color="secondary">Add movie 4</Button>

            <Button variant="outlined" onClick={() => removeMovieFromBasket("1")} color="primary">Remove movie 1</Button>
            <Button variant="outlined" onClick={() => removeMovieFromBasket("2")} color="primary">Remove movie 2</Button>
            <Button variant="outlined" onClick={() => removeMovieFromBasket("3")} color="primary">Remove movie 3</Button>
            <Button variant="outlined" onClick={() => removeMovieFromBasket("4")} color="primary">Remove movie 4</Button>

            <Button variant="outlined" onClick={() => setPayment(4)} color="primary">Set payment</Button>

            <h4>Movies:</h4>
            <pre>{JSON.stringify(getBasketMovies)}</pre>
            <h4>Payment:</h4>
            <pre>{JSON.stringify(getBasketPayment)}</pre>
        </>
    )
}