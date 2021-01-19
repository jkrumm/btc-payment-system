import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './fee.reducer';
import { IFee } from 'app/shared/model/fee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeeUpdate = (props: IFeeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { feeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/fee');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...feeEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="btcPaymentSystemApp.fee.home.createOrEditLabel" data-cy="FeeCreateUpdateHeading">
            Create or edit a Fee
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : feeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="fee-id">ID</Label>
                  <AvInput id="fee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="feeTypeLabel" for="fee-feeType">
                  Fee Type
                </Label>
                <AvInput
                  id="fee-feeType"
                  data-cy="feeType"
                  type="select"
                  className="form-control"
                  name="feeType"
                  value={(!isNew && feeEntity.feeType) || 'ZERO'}
                >
                  <option value="ZERO">ZERO</option>
                  <option value="LOW">LOW</option>
                  <option value="HIGH">HIGH</option>
                </AvInput>
                <UncontrolledTooltip target="feeTypeLabel">Fees can be ZERO, LOW, HIGH</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="percentLabel" for="fee-percent">
                  Percent
                </Label>
                <AvField
                  id="fee-percent"
                  data-cy="percent"
                  type="text"
                  name="percent"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
                <UncontrolledTooltip target="percentLabel">Percentage for a small amount transaction</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="percentSecureLabel" for="fee-percentSecure">
                  Percent Secure
                </Label>
                <AvField
                  id="fee-percentSecure"
                  data-cy="percentSecure"
                  type="text"
                  name="percentSecure"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
                <UncontrolledTooltip target="percentSecureLabel">Percentage for a high amount transaction</UncontrolledTooltip>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/fee" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  feeEntity: storeState.fee.entity,
  loading: storeState.fee.loading,
  updating: storeState.fee.updating,
  updateSuccess: storeState.fee.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeeUpdate);
