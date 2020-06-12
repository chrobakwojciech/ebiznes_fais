import {Grid, Paper} from "@material-ui/core";
import React, {useContext, useEffect, useState} from "react";
import Typography from "@material-ui/core/Typography";
import Rating from '@material-ui/lab/Rating';
import makeStyles from "@material-ui/core/styles/makeStyles";
import {Link, useHistory} from 'react-router-dom';
import Box from "@material-ui/core/Box";
import Divider from "@material-ui/core/Divider";
import Chip from "@material-ui/core/Chip";
import API from "../../utils/api/API";
import {movieApi} from "../../utils/api/movie.api";
import {UserContext} from "../../context/userContext/UserContext";


const useStyles = makeStyles({
    gridItem: {
        backgroundColor: 'transparent',
    },
    movieImg: {
        width: '100%'
    },
    movieTitle: {
        marginBlockStart: '0.3em',
        marginBlockEnd: '0.1em'
    },
    movieProductionYear: {
        color: '#ccc'
    },
    movieDescription: {
        marginBlockStart: '32px'
    },
    link: {
        textDecoration: 'none'
    },
    genres: {
        marginLeft: '16px'
    },
    genre: {
        borderRadius: 0,
        marginLeft: '4px'
    },
    actors: {
        marginTop: '16px'
    },
    actor: {
        marginLeft: '8px',
        color: 'white'
    },
    directors: {
        marginTop: '32px'
    },
    director: {
        marginLeft: '8px',
        color: 'white'
    }
});


export default function LibraryItem(props) {
    const movie = props.movie;
    const classes = useStyles();
    const {userCtx} = useContext(UserContext);

    let history = useHistory();
    const [genres, setGenres] = useState([]);
    const [actors, setActors] = useState([]);
    const [directors, setDirectors] = useState([]);
    const [rating, setRating] = useState(0);

    useEffect(() => {
        const fetchData = async () => {
            setGenres(await movieApi.getMovieGenre(movie.id));
            setActors(await movieApi.getMovieActors(movie.id));
            setDirectors(await movieApi.getMovieDirectors(movie.id));
            if (userCtx.user) {
                const ratings = await movieApi.getMovieRatings(movie.id);
                const userRatings = ratings.filter(rating => rating.user.id === userCtx.user.id);
                if (userRatings.length > 0) {
                    setRating(userRatings[0].value)
                }
            }
        };

        fetchData();
    }, [rating]);

    const handleGenreClick = (genreName) => {
        history.push(`/gatunek/${genreName.toLowerCase()}`);
    };

    const handleActorClick = (actorId) => {
        history.push(`/aktor/${actorId}`);
    };

    const handleDirectorClick = (directorId) => {
        history.push(`/rezyser/${directorId}`);
    };


    const onRatingChange = async (event, value) => {
        await API.post('/ratings', {
            value: value,
            movie: movie.id
        });
        setRating(value)
    };

    return (
        <Grid item xs={12}>
            <Paper elevation={0} className={classes.gridItem}>
                <Grid container spacing={3}>
                    <Grid item xs={2}>
                        <Link to={{ pathname: `/filmy/${movie.id}`}}>
                            <img className={classes.movieImg} src={movie.img} alt={movie.title}/>
                        </Link>

                    </Grid>
                    <Grid item xs={6}>
                        <Box>
                            <h1 className={classes.movieTitle}>{movie.title}</h1>
                            <Divider />
                            <Box display="flex" flexDirection="row" justifyContent="space-between">
                                <Box>
                                    <Typography component="span" className={classes.movieProductionYear} variant="subtitle1">{movie.productionYear}</Typography>
                                    <Box component="span" className={classes.genres}>
                                        {genres.map(genre => (
                                            <Chip size="small" key={genre.id} onClick={() => handleGenreClick(genre.name)} className={classes.genre} variant="outlined" color="secondary" label={genre.name} />
                                        ))}
                                    </Box>
                                </Box>
                                <Box>
                                    <Rating name={movie.title} onChange={onRatingChange} value={rating}  defaultValue={2} max={10} />
                                </Box>
                            </Box>
                            <Box>
                                <Typography className={classes.movieDescription}>{movie.description}</Typography>
                                <Divider />
                                <Box className={classes.directors}>
                                    <Typography component="span" variant="subtitle2">Reżyseria: </Typography>
                                    {directors.map(director => (
                                        <Chip key={director.id} onClick={() => handleDirectorClick(director.id)} className={classes.director} variant="outlined" label={`${director.firstName} ${director.lastName}`} />
                                    ))}
                                </Box>
                                <Box className={classes.actors}>
                                    <Typography component="span" variant="subtitle2">Obsada: </Typography>
                                    {actors.map(actor => (
                                        <Chip key={actor.id} onClick={() => handleActorClick(actor.id)} className={classes.actor} variant="outlined" label={`${actor.firstName} ${actor.lastName}`} />
                                    ))}
                                </Box>
                            </Box>
                        </Box>
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    )

}