import React, {useEffect, useState} from 'react';
import API from "../../../utils/api/API";
import {useParams} from "react-router-dom";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Rating from "@material-ui/lab/Rating/Rating";
import MovieComments from "./MovieComments";
import MovieRatings from "./MovieRatings";
import MovieDirectors from "./MovieDirectors";
import MovieActors from "./MovieActors";

export default function Movie(props) {
    const [movie, setMovie] = useState({});
    const [genres, setGenres] = useState([]);
    const [ratings, setRatings] = useState([]);
    const [rating, setRating] = useState(0);

    const urlParams = useParams();
    const movieId = urlParams.movieId;

    useEffect(() => {
        const fetchData = async () => {
            const url = `/movies/${urlParams.movieId}`;
            let res = await API.get(url);
            setMovie(res.data);

            res = await API.get(`${url}/genres`);
            setGenres(res.data);

            res = await API.get(`${url}/ratings`);
            let ratings = res.data;
            setRatings(ratings);

            if (ratings.length > 0) {
                const sum = ratings.reduce((a, b) => +a + +b.value, 0);
                setRating(sum/ratings.length)
            }

        };

        fetchData();
    }, [urlParams.movieId]);


    const onRatingChange = async (event, value) => {
        await API.post('/ratings', {
            value: value,
            user: '1',
            movie: movie.id
        });
        let res = await API.get(`/movies/${movie.id}/ratings`);
        let ratings = res.data;
        setRatings(ratings);
        if (ratings.length > 0) {
            const sum = ratings.reduce((a, b) => +a + +b.value, 0);
            setRating(sum/ratings.length)
        }
    };

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
                </Grid>
                <Grid item xs={5}>
                    <h1>{movie.title}</h1>
                    <Rating onChange={onRatingChange} value={rating} max={10} size="small" />
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