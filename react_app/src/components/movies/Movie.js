import React, {useEffect, useState} from 'react';
import API from "../../utils/API";
import {useParams} from "react-router-dom";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText";
import Rating from "@material-ui/lab/Rating/Rating";
import Typography from "@material-ui/core/Typography";

export default function Movie(props) {
    const [movie, setMovie] = useState({});
    const [actors, setActors] = useState([]);
    const [directors, setDirectors] = useState([]);
    const [genres, setGenres] = useState([]);
    const [comments, setComments] = useState([]);
    const [ratings, setRatings] = useState([]);
    const [rating, setRating] = useState(0);

    const urlParams = useParams();

    useEffect(() => {
        const fetchData = async () => {
            const url = `/movies/${urlParams.movieId}`;
            let res = await API.get(url);
            setMovie(res.data);

            res = await API.get(`${url}/actors`);
            setActors(res.data);

            res = await API.get(`${url}/directors`);
            setDirectors(res.data);

            res = await API.get(`${url}/genres`);
            setGenres(res.data);

            res = await API.get(`${url}/comments`);
            setComments(res.data);

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
        })
        let res = await API.get(`/movies/${movie.id}/ratings`);
        let ratings = res.data;
        setRatings(ratings);
        if (ratings.length > 0) {
            const sum = ratings.reduce((a, b) => +a + +b.value, 0);
            setRating(sum/ratings.length)
        }
    }

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
                    <h3>Reżyseria</h3>
                    {directors.map(director => (
                        <ListItem button>
                            <ListItemAvatar>
                                <Avatar
                                    alt={`${director.firstName} ${director.lastName}`}
                                    src={director.img}
                                />
                            </ListItemAvatar>
                            <ListItemText id={director.id} primary={`${director.firstName} ${director.lastName}`} />
                        </ListItem>
                    ))}

                    <h3>Obsada</h3>
                    {actors.map(actor => (
                        <ListItem button>
                            <ListItemAvatar>
                                <Avatar
                                    alt={`${actor.firstName} ${actor.lastName}`}
                                    src={actor.img}
                                />
                            </ListItemAvatar>
                            <ListItemText id={actor.id} primary={`${actor.firstName} ${actor.lastName}`} />
                        </ListItem>
                    ))}

                </Grid>
            </Grid>

            <Grid
                container
                direction="row"
                spacing={4}
            >
                <Grid item xs={3}>
                    <h3>Oceny</h3>
                    {ratings.map(rating => (
                        <ListItem>
                            <ListItemAvatar>
                                <Avatar
                                    alt={`${rating.user.firstName} ${rating.user.lastName}`}
                                    src={'http://via.placeholder.com/100x100'}
                                />
                            </ListItemAvatar>


                            <ListItemText
                                id={rating.id}
                                primary={`${rating.user.firstName} ${rating.user.lastName}`}
                                secondary={
                                    <React.Fragment>
                                        <Rating value={rating.value} max={10} size="small" readOnly/>
                                    </React.Fragment>
                                }
                            />
                        </ListItem>
                    ))}

                </Grid>
                <Grid item xs={6}>
                    <h3>Komentarze</h3>
                    {comments.map(comment => (
                        <ListItem>
                            <ListItemAvatar>
                                <Avatar
                                    alt={`${comment.user.firstName} ${comment.user.lastName}`}
                                    src={'http://via.placeholder.com/100x100'}
                                />
                            </ListItemAvatar>


                            <ListItemText
                                id={comment.id}
                                primary={`${comment.user.firstName} ${comment.user.lastName}`}
                                secondary={
                                    <React.Fragment>
                                        <Typography
                                            component="span"
                                            variant="body2"
                                            color="textPrimary"
                                        >{comment.content}</Typography>
                                    </React.Fragment>
                                }
                            />
                        </ListItem>
                    ))}

                </Grid>
            </Grid>



        </Box>

    );
}