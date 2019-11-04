import { Box, Button, Grid, Typography } from '@material-ui/core';
import { AddCircleOutlineRounded as AddIcon } from '@material-ui/icons';
import { addCarAction, changeCarsSortOrderAction, changeCurrentPageAction, changeSelectedMenuIndexAction, getCarsAction } from 'actions';
import { CarCard, ErrorPanel, LoadingPanel } from 'components';
import { AddCarModal } from 'modals';
import PropTypes from 'prop-types';
import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { CarPropType } from 'resources';

import './CarsOverviewPage.scss';

class CarsOverviewPage extends PureComponent {
    constructor(props) {
        super(props);

        this.state = { isAddCarModalOpen: false };

        this.onRegistrationButtonClick = this.onRegistrationButtonClick.bind(this);
        this.onOwnerButtonClick = this.onOwnerButtonClick.bind(this);
        this.onOpenAddCarModal = this.onOpenAddCarModal.bind(this);
        this.onValidateAddCarModel = this.onValidateAddCarModel.bind(this);
        this.onCloseAddCarModal = this.onCloseAddCarModal.bind(this);
    }

    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex, getCars, sortedBy } = this.props;

        changeCurrentPage('Voitures', []);
        changeSelectedMenuIndex(0);

        getCars(sortedBy);
    }

    onRegistrationButtonClick() {
        const { changeCarsSortOrder, getCars, sortedBy } = this.props;
        const registration = 'registration';

        if (sortedBy !== registration) {
            changeCarsSortOrder(registration);

            getCars(registration);
        }
    }

    onOwnerButtonClick() {
        const { changeCarsSortOrder, getCars, sortedBy } = this.props;
        const owner = 'owner';

        if (sortedBy !== owner) {
            changeCarsSortOrder(owner);

            getCars(owner);
        }
    }

    onOpenAddCarModal() {
        this.setState({ isAddCarModalOpen: true });
    };

    onValidateAddCarModel(car) {
        const { addCar, sortedBy } = this.props;

        addCar(car, sortedBy);
    }

    onCloseAddCarModal() {
        this.setState({ isAddCarModalOpen: false });
    };

    render() {
        const { cars, isGetAllInError, isGetAllInProgress, sortedBy } = this.props;
        const { isAddCarModalOpen } = this.state;

        let content;
        if (isGetAllInError) {
            content = <ErrorPanel className='ErrorPanel' />;
        } else if (isGetAllInProgress) {
            content = <LoadingPanel className='LoadingPanel' />;
        } else {
            const registrationButtonClassName = `HeaderGrid-SortSection-RegistrationButton ${'registration' === sortedBy
            && 'HeaderGrid-SortSection-RegistrationButton_selected'}`;
            const ownerButtonClassName = `HeaderGrid-SortSection-OwnerButton ${'owner' === sortedBy
            && 'HeaderGrid-SortSection-OwnerButton_selected'}`;

            content = (<Fragment>
                <Grid alignItems='center' className='HeaderGrid' container justify='space-between'>
                    <Grid item>
                        <Button className='HeaderGrid-AddCarButton' onClick={this.onOpenAddCarModal} variant='outlined'>
                            Ajouter

                            <AddIcon className='HeaderGrid-AddCarButton-Icon' />
                        </Button>
                    </Grid>

                    <Grid item>
                        <Box className='HeaderGrid-SortSection'>
                            <Button className={registrationButtonClassName} disableRipple
                                    onClick={this.onRegistrationButtonClick}>Immatriculation</Button>
                            <Typography className='HeaderGrid-SortSection-Separator non-selectable' variant='h6'>|</Typography>
                            <Button className={ownerButtonClassName} disableRipple onClick={this.onOwnerButtonClick}>Propriétaire</Button>
                        </Box>
                    </Grid>
                </Grid>

                <Grid className='CarsGrid' container justify='flex-start' alignItems='flex-start' spacing={4}>
                    {cars.map(car => <Grid className='CarsGrid-Item' item key={car.registration} lg={4} md={6} sm={12} xl={3} xs={12}>
                        <CarCard car={car} className='CarsGrid-Item-CarCard' />
                    </Grid>)}
                </Grid>
            </Fragment>);
        }

        return <Box id="CarsOverviewPage">
            {content}

            <AddCarModal open={isAddCarModalOpen} onClose={this.onCloseAddCarModal} onValidate={this.onValidateAddCarModel}
                         registrations={cars.map(car => car.registration)} />
        </Box>;
    }
}

const mapStateToProps = state => ({
    cars: state.cars.cars,
    sortedBy: state.cars.sortedBy,
    isGetAllInProgress: state.cars.isGetAllInProgress,
    isGetAllInError: state.cars.isGetAllInError
});

const mapDispatchToProps = dispatch => bindActionCreators({
    addCar: addCarAction,
    changeCarsSortOrder: changeCarsSortOrderAction,
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    getCars: getCarsAction
}, dispatch);

CarsOverviewPage.propTypes = {
    addCar: PropTypes.func.isRequired,
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    sortedBy: PropTypes.string.isRequired,
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    getCars: PropTypes.func.isRequired,
    isGetAllInError: PropTypes.bool.isRequired,
    isGetAllInProgress: PropTypes.bool.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(CarsOverviewPage);
