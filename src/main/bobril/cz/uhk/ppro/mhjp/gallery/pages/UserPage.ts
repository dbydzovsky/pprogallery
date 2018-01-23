import * as b from 'bobril'

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    id: "UserPage",
    render(ctx: IContext, me: b.IBobrilNode) {
        me.children = [
            "UserPage", // TODO asi to vyhodime
        ];
    }
});