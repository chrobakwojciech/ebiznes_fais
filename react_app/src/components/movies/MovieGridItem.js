import {Grid, Paper} from "@material-ui/core";
import React from "react";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles({
    root: {
        maxWidth: 345,
    },
    media: {
        filter: 'grayscale(85%)',
        transition: 'all 0.2s ease',
        "&:hover": {
            filter: 'grayscale(0%)',
        },
        height: 420,
    }
});

export default function MovieGridItem(props) {
    const movie = props.movie;
    const classes = useStyles();

    return (
        <Grid item xs={2}>
            <Card elevation={0} className={classes.root}>
                <CardActionArea>
                    <CardMedia
                        className={classes.media}
                        image={movie.img}
                        title={movie.title}
                    />
                    <CardContent>
                        <Typography align="center" variant="h5" component="h2">
                            {movie.title}
                        </Typography>
                    </CardContent>
                </CardActionArea>
            </Card>
        </Grid>
    )

}