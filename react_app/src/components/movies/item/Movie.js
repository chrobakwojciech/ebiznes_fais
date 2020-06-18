import React, {useEffect, useState} from 'react';
import API from "../../../utils/api/API";
import {useHistory, useParams} from "react-router-dom";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Rating from "@material-ui/lab/Rating/Rating";
import MovieComments from "./MovieComments";
import MovieRatings from "./MovieRatings";
import MovieDirectors from "./MovieDirectors";
import MovieActors from "./MovieActors";
import MovieControl from "./MovieControl";
import {movieApi} from "../../../utils/api/movie.api";

export default function Movie(props) {
    const [movie, setMovie] = useState({});
    const [genres, setGenres] = useState([]);
    const [ratings, setRatings] = useState([]);
    const [rating, setRating] = useState(0);

    const urlParams = useParams();
    const movieId = urlParams.movieId;
    let history = useHistory();

    useEffect(() => {
        const fetchData = async () => {
            const url = `/movies/${urlParams.movieId}`;
            let res;
            try {
                res = await movieApi.getById(movieId);
                setMovie(res);
            } catch (e) {
                history.push('/');
                return
            }


            res = await API.get(`${url}/genres`);
            setGenres(res.data);

            res = await API.get(`${url}/ratings`);
            let _ratings = res.data;
            setRatings(_ratings);

            if (_ratings.length > 0) {
                const sum = _ratings.reduce((a, b) => +a + +b.value, 0);
                setRating(sum/_ratings.length)
            }

        };

        fetchData();
    }, [history, movieId, urlParams.movieId]);


    return (
        <Box>
            <Grid
                container
                direction="row"
                justify="center"
                spacing={4}
            >
                <Grid item xs={3}>
                    <img width="100%" src={movie.img}/>
                    <MovieControl movieId={movieId} movieTitle={movie.title}/>
                </Grid>
                <Grid item xs={5}>
                    <h1>{movie.title}</h1>
                    <Rating value={rating} max={10} readOnly/>
                    <p>{movie.description}</p>
                </Grid>
                <Grid item xs={4}>
                    <MovieDirectors movieId={movieId}/>
                    <MovieActors movieId={movieId}/>
                </Grid>
            </Grid>

            <Grid container direction="row" spacing={4}>
                <Grid item xs={3}>
                    <MovieRatings ratings={ratings}/>
                </Grid>
                <Grid item xs={6}>
                    <MovieComments movieId={movieId}/>
                </Grid>
            </Grid>
        </Box>
    );
}
