import * as b from 'bobril'

export interface IData {
    text: string;
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "P";
        me.style = {

        };
        me.children = [
            ctx.data.text
        ]
    }
});