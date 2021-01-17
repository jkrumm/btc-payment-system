import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMerchantUser } from 'app/shared/model/merchant-user.model';

type EntityResponseType = HttpResponse<IMerchantUser>;
type EntityArrayResponseType = HttpResponse<IMerchantUser[]>;

@Injectable({ providedIn: 'root' })
export class MerchantUserService {
  public resourceUrl = SERVER_API_URL + 'api/merchant-users';

  constructor(protected http: HttpClient) {}

  create(merchantUser: IMerchantUser): Observable<EntityResponseType> {
    return this.http.post<IMerchantUser>(this.resourceUrl, merchantUser, { observe: 'response' });
  }

  update(merchantUser: IMerchantUser): Observable<EntityResponseType> {
    return this.http.put<IMerchantUser>(this.resourceUrl, merchantUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMerchantUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMerchantUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
