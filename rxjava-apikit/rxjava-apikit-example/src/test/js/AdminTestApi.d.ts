import TestForm from './form/TestForm'
import {AbstractApi} from 'rxjava-api-core'


/**
 * 
*/
declare class AdminTestApi extends AbstractApi {


   /**
     * 
    *
     * <div class='http-info'>http 说明<ul>
     * <li><b>Uri:</b>admin/testPath/{id}</li>
     * <li><b>PathVariable:</b> string id</li>
     * <li><b>Form:</b>TestFormtestPath</li>
     * <li><b>Model:</b> number</li>
     * <li>需要登录</li>
     * </ul>
     * </div>
     * @see TestForm
    */
    testPath(id:string, form:TestForm):Promise<number>;

}
export { AdminTestApi };
declare const adminTestApi: AdminTestApi;
export default adminTestApi;