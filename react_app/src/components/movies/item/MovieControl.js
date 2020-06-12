import React, {useContext, useEffect, useState} from "react";
import Button from "@material-ui/core/Button";
import {PlayCircleOutline} from '@material-ui/icons';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import {UserContext} from "../../../context/UserContext";
import {genreApi} from "../../../utils/api/genre.api";
import {movieApi} from "../../../utils/api/movie.api";

export default function MovieControl({movieId, movieTitle}) {
    const [open, setOpen] = useState(false);
    const [isOrdered, setIsOrdered] = useState(false);
    const {userCtx} = useContext(UserContext);

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
            setIsOrdered(movieIds.includes(movieId));
        };

        fetchData();
    }, []);


    if (!isOrdered) {
        return null
    } else {
        return (
            <>
                <Button onClick={handleClickOpen} startIcon={<PlayCircleOutline />} fullWidth size="large" variant="contained" color="secondary">
                    Oglądaj
                </Button>

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