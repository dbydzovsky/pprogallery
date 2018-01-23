import * as b from 'bobril'
import {create as panel} from '../components/panelNewPost'

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    id: "Gallery",
    render(ctx: IContext, me: b.IBobrilNode) {
        me.children = [
            panel(),
        ];
    }
});