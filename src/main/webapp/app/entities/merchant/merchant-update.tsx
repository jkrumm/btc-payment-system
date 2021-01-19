import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFee } from 'app/shared/model/fee.model';
import { getEntities as getFees } from 'app/entities/fee/fee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './merchant.reducer';
import { IMerchant } from 'app/shared/model/merchant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMerchantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MerchantUpdate = (props: IMerchantUpdateProps) => {
  const [feeId, setFeeId] = useState('0');
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { merchantEntity, fees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/merchant');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFees();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...merchantEntity,
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
          <h2 id="btcPaymentSystemApp.merchant.home.createOrEditLabel" data-cy="MerchantCreateUpdateHeading">
            Create or edit a Merchant
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : merchantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="merchant-id">ID</Label>
                  <AvInput id="merchant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="merchant-name">
                  Name
                </Label>
                <AvField
                  id="merchant-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
                <UncontrolledTooltip target="nameLabel">Merchant name</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="merchant-email">
                  Email
                </Label>
                <AvField
                  id="merchant-email"
                  data-cy="email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    pattern: {
                      value: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$',
                      errorMessage: "This field should follow pattern for '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+..",
                    },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="merchant-fee">Fee</Label>
                <AvInput id="merchant-fee" data-cy="fee" type="select" className="form-control" name="fee.id">
                  <option value="" key="0" />
                  {fees
                    ? fees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/merchant" replace color="info">
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
  fees: storeState.fee.entities,
  merchantEntity: storeState.merchant.entity,
  loading: storeState.merchant.loading,
  updating: storeState.merchant.updating,
  updateSuccess: storeState.merchant.updateSuccess,
});

const mapDispatchToProps = {
  getFees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MerchantUpdate);
