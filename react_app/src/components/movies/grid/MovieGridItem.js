import {Grid, Paper} from "@material-ui/core";
import React from "react";
import Typography from "@material-ui/core/Typography";
import Rating from '@material-ui/lab/Rating';
import makeStyles from "@material-ui/core/styles/makeStyles";
import {Link} from 'react-router-dom';
import Box from "@material-ui/core/Box";


const useStyles = makeStyles({
    gridItem: {
      backgroundColor: 'transparent',
        height: '100%',
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
    link: {
        textDecoration: 'none'
    }
});

export default function MovieGridItem(props) {
    const movie = props.movie;
    const classes = useStyles();

    return (
        <Grid item  xl={2} lg={3} md={4} sm={6} xs={12}>
            <Link className={classes.link} to={{ pathname: `/filmy/${movie.id}`}}>
                <Paper elevation={0} className={classes.gridItem}>
                    <Box className={classes.gridItem} display="flex" flexDirection="column" justifyContent="space-between">
                        <Box>
                            <img className={classes.movieImg} src={movie.img} alt={movie.title}/>
                        </Box>
                        <Box>
                            <h2 className={classes.movieTitle}>{movie.title}</h2>
                            <Box display="flex" flexDirection="row" justifyContent="space-between">
                                <Box>
                                    <Typography component="span" className={classes.movieProductionYear} variant="subtitle2">{movie.productionYear}</Typography>
                                </Box>
                                <Box>
                                    <Rating name="half-rating-read size-small" defaultValue={movie.rating/2} precision={0.5} size="small" readOnly />
                                </Box>
                            </Box>
                        </Box>
                    </Box>
                </Paper>
            </Link>
        </Grid>
    )

}