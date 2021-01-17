import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFee } from 'app/shared/model/fee.model';

type EntityResponseType = HttpResponse<IFee>;
type EntityArrayResponseType = HttpResponse<IFee[]>;

@Injectable({ providedIn: 'root' })
export class FeeService {
  public resourceUrl = SERVER_API_URL + 'api/fees';

  constructor(protected http: HttpClient) {}

  create(fee: IFee): Observable<EntityResponseType> {
    return this.http.post<IFee>(this.resourceUrl, fee, { observe: 'response' });
  }

  update(fee: IFee): Observable<EntityResponseType> {
    return this.http.put<IFee>(this.resourceUrl, fee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
