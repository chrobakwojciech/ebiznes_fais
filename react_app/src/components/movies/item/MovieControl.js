import React, {useContext, useEffect, useState} from "react";
import Button from "@material-ui/core/Button";
import {AddCircle, DeleteForever, LibraryAddCheck, PlayCircleOutline} from '@material-ui/icons';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import {movieApi} from "../../../utils/api/movie.api";
import {BasketContext} from "../../../context/basketContext/BasketContext";
import green from "@material-ui/core/colors/green";
import {createMuiTheme} from "@material-ui/core";
import {ThemeProvider} from "@material-ui/styles";
import Grid from "@material-ui/core/Grid";
import {blue, red} from "@material-ui/core/colors";
import {NavLink} from "react-router-dom";
import ListItem from "@material-ui/core/ListItem";

const basketButtonTheme = createMuiTheme({
    palette: {
        primary: green,
        secondary: red
    },
});

const watchMovieTheme = createMuiTheme({
    palette: {
        primary: blue
    },
});

export default function MovieControl({movieId, movieTitle}) {
    const [open, setOpen] = useState(false);
    const [isBought, setIsBought] = useState(false);
    const {addMovieToBasket, removeMovieFromBasket, isMovieAlreadyAdded} = useContext(BasketContext);

    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        const fetchData = async () => {
            const movies = await movieApi.getForUser();
            const movieIds = movies.map(m => m.id);
            setIsBought(movieIds.includes(movieId));
        };

        fetchData();
    }, []);


    const BasketButton = () => {
        if (isMovieAlreadyAdded(movieId)) {
            return (
                <ThemeProvider theme={basketButtonTheme}>
                    <Grid container spacing={1}>
                        <Grid item xs={3}>
                            <Button onClick={() => removeMovieFromBasket(movieId)} startIcon={<DeleteForever />} fullWidth variant="contained" color="secondary">Usuń</Button>
                        </Grid>
                        <Grid item xs={9}>
                            <Button startIcon={<LibraryAddCheck />} fullWidth variant="contained" color="primary" component={NavLink} exact={true} to="/koszyk">
                                Przejdź do koszyka
                            </Button>
                        </Grid>
                    </Grid>


                </ThemeProvider>

            )
        } else {
            return (
                <Button onClick={() => addMovieToBasket(movieId)} startIcon={<AddCircle />} fullWidth variant="contained" color="secondary">
                    Dodaj do koszyka
                </Button>
            )
        }
    };

    if (!isBought) {
        return (
            <BasketButton/>
        )
    } else {
        return (
            <>
                <ThemeProvider theme={watchMovieTheme}>
                    <Button onClick={handleClickOpen} startIcon={<PlayCircleOutline />} fullWidth size="large" variant="contained" color="primary">
                        Oglądaj
                    </Button>
                </ThemeProvider>

                <Dialog maxWidth={"xl"} onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
                    <DialogTitle id="customized-dialog-title" onClose={handleClose}>
                        {movieTitle}
                    </DialogTitle>
                    <DialogContent dividers>
                        <iframe width="1120" height="630" src="https://www.youtube.com/embed/DhNMHcRSNdo" frameBorder="0"
                                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                                allowFullScreen/>
                    </DialogContent>
                </Dialog>
            </>
        );
    }

}