import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { changeCurrentPageAction, changeSelectedMenuIndexAction, getCarsAction } from "actions";

import { Grid } from '@material-ui/core';

import { CarCard, Loading } from 'components';

import StyledCarsPage from './StyledCarsPage';

class CarsPage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex, getCars } = this.props;

        changeCurrentPage('Voitures', []);

        getCars();

        changeSelectedMenuIndex(0)
    }

    render() {
        const { cars, isLoading, isInError } = this.props;

        let content;
        if (isInError) {
            content = "Error";
        } else if (isLoading) {
            content = <Loading />;
        } else {
            content = (
                <Grid container
                      justify="flex-start"
                      alignItems="flex-start"
                      spacing={4}>
                    {cars.map(car =>
                        <Grid item xs={3} ms={2}>
                            <CarCard car={car} />
                        </Grid>
                    )}
                </Grid>
            );
        }

        return (
            <StyledCarsPage>
                {content}
            </StyledCarsPage>
        );
    }
}

const mapStateToProps = state => ({
    cars: state.cars.cars,
    isLoading: state.cars.isLoading,
    isInError: state.cars.isInError
});

const mapDispatchToProps = dispatch => bindActionCreators({
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction,
        getCars: getCarsAction
    }, dispatch
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CarsPage);
