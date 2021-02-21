import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import {
    Box, Chip, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, Switch, Table, TableBody, TableCell, TableRow, TextField,
    Typography
} from '@material-ui/core';
import { RefreshRounded as RefreshIcon } from '@material-ui/icons';

import { getCarsAction, updateCurrentPageAction, updateMenuItemsAction, updateSelectedMenuIndexAction } from 'actions';
import { BottomBar, ErrorPanel, InterventionPreview, LoadingPanel, Page } from 'components';
import { addLeadingZeros, INTERVENTION_STATUS_STEPS, InterventionPropType } from 'resources';

import './InterventionsOverviewPage.scss';

// Declares the constants
const filterPanelTitleLabel = 'Filtres';
const hideClosedInterventionsFilterLabel = 'Cacher interventions terminées';
const interventionNumberFilterLabel = 'Numéro d\'intervention';
const interventionPanelTitleLabel = 'Interventions';
const noInterventionLabel = 'Aucune intervention à afficher';
const overviewPanelTitleLabel = 'Statistiques';
const overviewPanelInterventionsRowLabel = 'Interventions';
const overviewPanelOperationsRowLabel = 'Opérations';
const overviewPanelOperationLinesRowLabel = 'Lignes d\'opérations';
const registrationFilterLabel = 'Numéro d\'immatriculation';

const isInterventionClosed = (intervention) => {
    return intervention.status === INTERVENTION_STATUS_STEPS[4]
            && (intervention.paidAmount && intervention.amount && intervention.paidAmount >= intervention.amount)
            && 0 === intervention.operations.filter(operation => operation.operationLines.some(line => !line.done)).length;
};

const doesInterventionMatchNumber = (intervention, number) => {
    return `${intervention.year}-${addLeadingZeros(intervention.number, 2)}`.includes(number);
};

const doesInterventionMatchRegistration = (intervention, registration) => {
    return intervention.carRegistration.toLowerCase()
            .includes(registration.toLowerCase());
};

/**
 * The interventions overview page component.
 *
 * @param {object[]} interventions
 *    The list of all the interventions
 * @param {boolean} isInError
 *    If the interventions loading is in error
 * @param {boolean} isLoading
 *    If the interventions loading is in progress
 * @param {func} updateCurrentPage
 *     The {@link updateCurrentPageAction} action
 * @param {func} updateSelectedMenuIndex
 *     The {@link updateSelectedMenuIndexAction} action
 *
 * @class
 */
class InterventionsOverviewPage extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);

        // Initializes the component state
        this.state = {
            expandedInterventionIndex: false,
            hideClosedInterventions: true,
            interventionNumberFilterValue: '',
            registrationFilterValue: ''
        };

        // Binds the local methods
        this.onHideClosedInterventionsSwitchClick = this.onHideClosedInterventionsSwitchClick.bind(this);
        this.onInterventionPreviewClick = this.onInterventionPreviewClick.bind(this);
        this.onInterventionNumberFilterChange = this.onInterventionNumberFilterChange.bind(this);
        this.onRegistrationFilterChange = this.onRegistrationFilterChange.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const {
            getCars,
            updateCurrentPage,
            updateMenuItems,
            updateSelectedMenuIndex
        } = this.props;

        updateCurrentPage('Interventions', []);
        updateMenuItems([ {
            icon: <RefreshIcon />,
            label: 'Rafraîchir',
            onClick: getCars
        } ]);
        updateSelectedMenuIndex(1);
    }

    /**
     * Handles a hide closed interventions switch click action.
     *
     * @param e
     *     The event to handle
     */
    onHideClosedInterventionsSwitchClick(e) {
        this.setState({ hideClosedInterventions: e.target.checked });
    }

    /**
     * Handles an intervention preview panel click action.
     * <br/>
     * This method is used to create an accordion effect on the interventions previews.
     *
     * @param index
     *    The index of the expanded intervention preview panel
     */
    onInterventionPreviewClick(index) {
        this.setState({ expandedInterventionIndex: this.state.expandedInterventionIndex === index ? false : index });
    }

    /**
     * Handles an intervention number filter value change action.
     *
     * @param e
     *    The event to handle
     */
    onInterventionNumberFilterChange(e) {
        this.setState({ interventionNumberFilterValue: e.target.value });
    }

    /**
     * Handles an registration filter value change action.
     *
     * @param e
     *    The event to handle
     */
    onRegistrationFilterChange(e) {
        this.setState({ registrationFilterValue: e.target.value });
    }

    /**
     * Render method.
     */
    render() {
        const {
            interventions,
            isInError,
            isLoading
        } = this.props;
        const {
            expandedInterventionIndex,
            hideClosedInterventions,
            interventionNumberFilterValue,
            registrationFilterValue
        } = this.state;

        let content;
        if (isInError) {
            // If the interventions or the cars failed to be loaded, displays the error panel
            content = (<ErrorPanel className='InterventionsOverviewPage_ErrorPanel' />);
        } else if (isLoading) {
            // If the interventions or the cars are being loaded, displays the loading panel
            content = (<LoadingPanel className='InterventionsOverviewPage_LoadingPanel' />);
        } else {
            // If the interventions and the cars have been loaded, displays the page normal content
            const interventionsNumber = interventions.length;
            const finishedInterventionsNumber = interventions.filter(isInterventionClosed).length;
            const areAllInterventionsFinished = interventionsNumber === finishedInterventionsNumber;

            const operations = interventions.flatMap(intervention => intervention.operations);
            const operationsNumber = operations.length;
            const finishedOperationsNumber = operations.filter(operation => !operation.operationLines.some(line => !line.done)).length;
            const areAllOperationsFinished = operationsNumber === finishedOperationsNumber;

            const operationLines = operations.flatMap(operation => operation.operationLines);
            const operationLinesNumber = operationLines.length;
            const finishedOperationLinesNumber = operationLines.filter(operationLine => !operationLine.done).length;
            const areAllOperationLinesFinished = operationLinesNumber === finishedOperationLinesNumber;

            let interventionsToDisplay = interventions;

            // If the closed interventions are to be hidden, applies a filter on the interventions to display
            if (hideClosedInterventions) {
                interventionsToDisplay = interventionsToDisplay.filter(intervention => !isInterventionClosed(intervention));
            }

            // If the intervention number filter value is set, applies a filter on the interventions to display
            if (interventionNumberFilterValue) {
                interventionsToDisplay =
                        interventionsToDisplay.filter(intervention => doesInterventionMatchNumber(intervention, interventionNumberFilterValue));
            }

            // If the registration number filter value is set, applies a filter on the interventions to display
            if (registrationFilterValue) {
                interventionsToDisplay =
                        interventionsToDisplay.filter(intervention => doesInterventionMatchRegistration(intervention, registrationFilterValue));
            }

            const interventionsPanelContent = (0 === interventionsToDisplay.length
                    ? <Typography align='center' className='InterventionsOverviewPage_InterventionsPanel_Content_NoInterventionsLabel'
                                  variant='body1'>
                        {noInterventionLabel}
                    </Typography>
                    : <Grid container>
                        {interventionsToDisplay.map((intervention, index) =>
                                <InterventionPreview className='InterventionsOverviewPage_InterventionsPanel_Content_InterventionPreview'
                                                     intervention={intervention} expanded={expandedInterventionIndex === index} key={index}
                                                     onClick={() => this.onInterventionPreviewClick(index)} />)}
                    </Grid>);

            content = (<Grid container spacing={4}>
                <Grid item xs={4}>
                    <Box className='InterventionsOverviewPage_StaticColumn'>
                        <ExpansionPanel className='InterventionsOverviewPage_StaticColumn_OverviewPanel' expanded={true}>
                            <ExpansionPanelSummary className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Summary'>
                                <Typography className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Summary_Label' color='primary'
                                            variant='h6'>
                                    {overviewPanelTitleLabel}
                                </Typography>
                            </ExpansionPanelSummary>

                            <ExpansionPanelDetails className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content'>
                                <Table className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table'>
                                    <TableBody>
                                        <TableRow className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row' hover>
                                            <TableCell className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Label'>
                                                {overviewPanelInterventionsRowLabel}
                                            </TableCell>
                                            <TableCell align='right'
                                                       className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Value'>
                                                <Chip className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Value_Chip'
                                                      color={areAllInterventionsFinished ? 'secondary' : 'primary'}
                                                      label={`${finishedInterventionsNumber} ⋮ ${interventionsNumber}`} size='small'
                                                      variant={areAllInterventionsFinished ? 'outlined' : 'default'} />
                                            </TableCell>
                                        </TableRow>

                                        <TableRow className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row' hover>
                                            <TableCell className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Label'>
                                                {overviewPanelOperationsRowLabel}
                                            </TableCell>
                                            <TableCell align='right'
                                                       className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Value'>
                                                <Chip className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Value_Chip'
                                                      color={areAllOperationsFinished ? 'secondary' : 'primary'}
                                                      label={`${finishedOperationsNumber} ⋮ ${operationsNumber}`} size='small'
                                                      variant={areAllOperationsFinished ? 'outlined' : 'default'} />
                                            </TableCell>
                                        </TableRow>

                                        <TableRow className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row' hover>
                                            <TableCell className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Label'>
                                                {overviewPanelOperationLinesRowLabel}
                                            </TableCell>
                                            <TableCell align='right'
                                                       className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Value'>
                                                <Chip className='InterventionsOverviewPage_StaticColumn_OverviewPanel_Content_Table_Row_Value_Chip'
                                                      color={areAllOperationLinesFinished ? 'secondary' : 'primary'}
                                                      label={`${finishedOperationLinesNumber} ⋮ ${operationLinesNumber}`} size='small'
                                                      variant={areAllOperationLinesFinished ? 'outlined' : 'default'} />
                                            </TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </ExpansionPanelDetails>

                            <BottomBar />
                        </ExpansionPanel>

                        <ExpansionPanel className='InterventionsOverviewPage_StaticColumn_FilterPanel' expanded={true}>
                            <ExpansionPanelSummary className='InterventionsOverviewPage_StaticColumn_FilterPanel_Summary'>
                                <Typography className='InterventionsOverviewPage_StaticColumn_FilterPanel_Summary_Label' color='primary'
                                            variant='h6'>
                                    {filterPanelTitleLabel}
                                </Typography>
                            </ExpansionPanelSummary>

                            <ExpansionPanelDetails className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content'>
                                <Table className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table'>
                                    <TableBody>
                                        <TableRow className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row'>
                                            <TableCell className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Label'>
                                                {interventionNumberFilterLabel}
                                            </TableCell>
                                            <TableCell align='right'
                                                       className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Value'>
                                                <TextField
                                                        className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Value_Field'
                                                        onChange={this.onInterventionNumberFilterChange} value={interventionNumberFilterValue}
                                                        variant='outlined' />
                                            </TableCell>
                                        </TableRow>

                                        <TableRow className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row'>
                                            <TableCell className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Label'>
                                                {registrationFilterLabel}
                                            </TableCell>
                                            <TableCell align='right'
                                                       className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Value'>
                                                <TextField
                                                        className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Value_Field'
                                                        onChange={this.onRegistrationFilterChange} value={registrationFilterValue}
                                                        variant='outlined' />
                                            </TableCell>
                                        </TableRow>

                                        <TableRow className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row'>
                                            <TableCell className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Label'>
                                                {hideClosedInterventionsFilterLabel}
                                            </TableCell>
                                            <TableCell align='right'
                                                       className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Value'>
                                                <Switch checked={hideClosedInterventions}
                                                        className='InterventionsOverviewPage_StaticColumn_FilterPanel_Content_Table_Row_Value_Switch'
                                                        color='primary' onChange={this.onHideClosedInterventionsSwitchClick} />
                                            </TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </ExpansionPanelDetails>

                            <BottomBar />
                        </ExpansionPanel>
                    </Box>
                </Grid>

                <Grid item xs={8}>
                    <ExpansionPanel className='InterventionsOverviewPage_InterventionsPanel' expanded>
                        <ExpansionPanelSummary className='InterventionsOverviewPage_InterventionsPanel_Summary'>
                            <Typography className='InterventionsOverviewPage_InterventionsPanel_Summary_Label' color='primary' variant='h6'>
                                {interventionPanelTitleLabel}
                            </Typography>
                        </ExpansionPanelSummary>

                        <ExpansionPanelDetails className='InterventionsOverviewPage_InterventionsPanel_Content'>
                            {interventionsPanelContent}
                        </ExpansionPanelDetails>

                        <BottomBar />
                    </ExpansionPanel>
                </Grid>
            </Grid>);
        }

        return (<Page id='InterventionsOverviewPage'>
            {content}
        </Page>);
    }
}

const mapStateToProps = (state) => ({
    interventions: state.cars.cars
            .flatMap(car => car.interventions.map(intervention => {
                return {
                    ...intervention,
                    carId: car.id,
                    carRegistration: car.registration
                };
            })),
    isInError: state.cars.isGetInError,
    isLoading: state.cars.isGetInProgress
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    getCars: getCarsAction,
    updateCurrentPage: updateCurrentPageAction,
    updateMenuItems: updateMenuItemsAction,
    updateSelectedMenuIndex: updateSelectedMenuIndexAction
}, dispatch);

InterventionsOverviewPage.propTypes = {
    interventions: PropTypes.arrayOf(InterventionPropType).isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
    updateCurrentPage: PropTypes.func.isRequired,
    updateMenuItems: PropTypes.func.isRequired,
    updateSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(InterventionsOverviewPage);
