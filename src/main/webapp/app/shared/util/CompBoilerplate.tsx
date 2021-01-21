import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
import { logout } from 'app/shared/reducers/authentication';

export interface IProps extends StateProps, DispatchProps {
  state: string;
}

const CompBoilerplate = (props: IProps) => {
  const [state, setState] = useState(props.state);
  const { auth } = props;

  // On Rendering
  useEffect(() => {
    // setState('init');
  }, []);

  return (
    <div>
      {auth.account.firstName}
      {state}
    </div>
  );
};

const mapStateToProps = ({ authentication }: IRootState) => ({
  auth: authentication,
});

const mapDispatchToProps = { logout };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompBoilerplate);
